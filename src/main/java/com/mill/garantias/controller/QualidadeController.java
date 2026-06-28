package com.mill.garantias.controller;

import com.mill.garantias.model.AnaliseQualidade;
import com.mill.garantias.service.AnaliseQualidadeService;
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

@Controller
@RequestMapping("/qualidade")
public class QualidadeController {

    @Autowired
    private AnaliseQualidadeService analiseService;

    @Autowired
    private ReclamacaoService reclamacaoService;

    @Autowired
    private GarantiaService garantiaService;

    @GetMapping
    public String lista(
            @RequestParam(defaultValue = "") String produto,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String numero,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        PageRequest pageRequest = PageRequest.of(pagina, 10, Sort.by("id").descending());
        Page<AnaliseQualidade> page = analiseService.listar(produto, status, numero, pageRequest);

        model.addAttribute("analises", page.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("totalItens", page.getTotalElements());
        model.addAttribute("filtroProduto", produto);
        model.addAttribute("filtroStatus", status);
        model.addAttribute("filtroNumero", numero);
        return "qualidade/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("analise", new AnaliseQualidade());
        model.addAttribute("titulo", "Nova Análise de Qualidade");
        model.addAttribute("reclamacoes", reclamacaoService.listarTodas());
        model.addAttribute("garantias", garantiaService.listarTodas());
        return "qualidade/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        return analiseService.buscarPorId(id).map(a -> {
            model.addAttribute("analise", a);
            model.addAttribute("titulo", "Editar Análise de Qualidade");
            model.addAttribute("reclamacoes", reclamacaoService.listarTodas());
            model.addAttribute("garantias", garantiaService.listarTodas());
            return "qualidade/formulario";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("erro", "Análise não encontrada.");
            return "redirect:/qualidade";
        });
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute AnaliseQualidade analise,
            @RequestParam(value = "reclamacaoId", required = false) Long reclamacaoId,
            @RequestParam(value = "garantiaId", required = false) Long garantiaId,
            RedirectAttributes redirectAttrs) {

        try {
            if (reclamacaoId != null) {
                reclamacaoService.buscarPorId(reclamacaoId).ifPresent(analise::setReclamacao);
            } else {
                analise.setReclamacao(null);
            }

            if (garantiaId != null) {
                garantiaService.buscarPorId(garantiaId).ifPresent(analise::setGarantia);
            } else {
                analise.setGarantia(null);
            }

            analiseService.salvar(analise);
            redirectAttrs.addFlashAttribute("sucesso", "Análise salva com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao salvar análise: " + e.getMessage());
        }
        return "redirect:/qualidade";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            analiseService.excluir(id);
            redirectAttrs.addFlashAttribute("sucesso", "Análise excluída com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/qualidade";
    }
}
