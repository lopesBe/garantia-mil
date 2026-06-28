package com.mill.garantias.controller;

import com.mill.garantias.model.Reclamacao;
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
@RequestMapping("/reclamacoes")
public class ReclamacaoController {

    @Autowired
    private ReclamacaoService reclamacaoService;

    @GetMapping
    public String lista(
            @RequestParam(defaultValue = "") String cliente,
            @RequestParam(defaultValue = "") String situacao,
            @RequestParam(defaultValue = "") String numero,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        PageRequest pageRequest = PageRequest.of(pagina, 10, Sort.by("id").descending());
        Page<Reclamacao> page = reclamacaoService.listar(cliente, situacao, numero, pageRequest);

        model.addAttribute("reclamacoes", page.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", page.getTotalPages());
        model.addAttribute("totalItens", page.getTotalElements());
        model.addAttribute("filtroCliente", cliente);
        model.addAttribute("filtroSituacao", situacao);
        model.addAttribute("filtroNumero", numero);
        return "reclamacoes/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("reclamacao", new Reclamacao());
        model.addAttribute("titulo", "Nova Reclamação");
        return "reclamacoes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        return reclamacaoService.buscarPorId(id).map(r -> {
            model.addAttribute("reclamacao", r);
            model.addAttribute("titulo", "Editar Reclamação");
            return "reclamacoes/formulario";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("erro", "Reclamação não encontrada.");
            return "redirect:/reclamacoes";
        });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Reclamacao reclamacao, RedirectAttributes redirectAttrs) {
        try {
            reclamacaoService.salvar(reclamacao);
            redirectAttrs.addFlashAttribute("sucesso", "Reclamação salva com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao salvar reclamação: " + e.getMessage());
        }
        return "redirect:/reclamacoes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            reclamacaoService.excluir(id);
            redirectAttrs.addFlashAttribute("sucesso", "Reclamação excluída com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/reclamacoes";
    }
}
