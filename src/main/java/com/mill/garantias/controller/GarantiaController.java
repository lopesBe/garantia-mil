package com.mill.garantias.controller;

import com.mill.garantias.model.Garantia;
import com.mill.garantias.model.GarantiaItem;
import com.mill.garantias.model.Reclamacao;
import com.mill.garantias.service.GarantiaService;
import com.mill.garantias.service.ReclamacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/garantia")
public class GarantiaController {

    @Autowired
    private GarantiaService garantiaService;

    @Autowired
    private ReclamacaoService reclamacaoService;

    @GetMapping
    public String lista(
            @RequestParam(defaultValue = "") String cliente,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String numero,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        PageRequest pageRequest = PageRequest.of(pagina, 10, Sort.by("id").descending());
        Page<Garantia> page = garantiaService.listar(cliente, status, numero, pageRequest);

        model.addAttribute("garantias", page.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("totalItens", page.getTotalElements());
        model.addAttribute("filtroCliente", cliente);
        model.addAttribute("filtroStatus", status);
        model.addAttribute("filtroNumero", numero);
        return "garantia/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        Garantia garantia = new Garantia();
        garantia.setItens(new ArrayList<>());
        model.addAttribute("garantia", garantia);
        model.addAttribute("titulo", "Nova Solicitação de Garantia");
        model.addAttribute("reclamacoes", reclamacaoService.listarTodas());
        return "garantia/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        return garantiaService.buscarPorId(id).map(g -> {
            model.addAttribute("garantia", g);
            model.addAttribute("titulo", "Editar Solicitação de Garantia");
            model.addAttribute("reclamacoes", reclamacaoService.listarTodas());
            return "garantia/formulario";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("erro", "Garantia não encontrada.");
            return "redirect:/garantia";
        });
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute Garantia garantia,
            @RequestParam(value = "item.codigo", required = false) List<String> codigos,
            @RequestParam(value = "item.descricao", required = false) List<String> descricoes,
            @RequestParam(value = "item.quantidade", required = false) List<Integer> quantidades,
            @RequestParam(value = "item.unidade", required = false) List<String> unidades,
            @RequestParam(value = "reclamacaoId", required = false) Long reclamacaoId,
            RedirectAttributes redirectAttrs) {

        try {
            // Vincula reclamação
            if (reclamacaoId != null) {
                reclamacaoService.buscarPorId(reclamacaoId).ifPresent(garantia::setReclamacao);
            } else {
                garantia.setReclamacao(null);
            }

            // Monta lista de itens
            List<GarantiaItem> itens = new ArrayList<>();
            if (codigos != null) {
                for (int i = 0; i < codigos.size(); i++) {
                    String cod = codigos.get(i);
                    if (cod != null && !cod.isBlank()) {
                        GarantiaItem item = new GarantiaItem();
                        item.setCodigo(cod);
                        item.setDescricao(descricoes != null && i < descricoes.size() ? descricoes.get(i) : "");
                        item.setQuantidade(quantidades != null && i < quantidades.size() ? quantidades.get(i) : 0);
                        item.setUnidade(unidades != null && i < unidades.size() ? unidades.get(i) : "UN");
                        item.setGarantia(garantia);
                        itens.add(item);
                    }
                }
            }
            garantia.setItens(itens);

            garantiaService.salvar(garantia);
            redirectAttrs.addFlashAttribute("sucesso", "Garantia salva com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao salvar garantia: " + e.getMessage());
        }
        return "redirect:/garantia";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            garantiaService.excluir(id);
            redirectAttrs.addFlashAttribute("sucesso", "Garantia excluída com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/garantia";
    }
}
