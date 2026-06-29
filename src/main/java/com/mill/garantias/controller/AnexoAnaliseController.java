package com.mill.garantias.controller;

import com.mill.garantias.model.AnexoAnalise;
import com.mill.garantias.model.AnaliseQualidade;
import com.mill.garantias.service.AnexoAnaliseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/anexos")
public class AnexoAnaliseController {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Autowired
    private AnexoAnaliseService anexoService;

    @PostMapping("/upload/{analiseId}")
    public String uploadAnexos(@PathVariable Long analiseId,
                               @RequestParam("files") MultipartFile[] files,
                               RedirectAttributes redirectAttrs) {
        Optional<AnaliseQualidade> analiseOptional = anexoService.buscarAnalise(analiseId);
        if (analiseOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("erro", "Análise de qualidade não encontrada.");
            return "redirect:/qualidade";
        }

        AnaliseQualidade analise = analiseOptional.get();
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                String filename = System.currentTimeMillis() + "_" + originalFilename;
                Path targetPath = Paths.get(uploadDir).resolve(filename);
                Files.copy(file.getInputStream(), targetPath);

                AnexoAnalise anexo = new AnexoAnalise();
                anexo.setNomeArquivo(originalFilename);
                anexo.setCaminho(targetPath.toAbsolutePath().toString());
                anexo.setTipo(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
                anexo.setAnaliseQualidade(analise);
                anexoService.salvar(anexo);
            } catch (IOException e) {
                redirectAttrs.addFlashAttribute("erro", "Erro ao enviar o arquivo: " + originalFilename);
                return "redirect:/qualidade/" + analiseId + "/editar";
            }
        }

        redirectAttrs.addFlashAttribute("sucesso", "Arquivo(s) enviados com sucesso.");
        return "redirect:/qualidade/" + analiseId + "/editar";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws IOException {
        Optional<AnexoAnalise> optional = anexoService.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AnexoAnalise anexo = optional.get();
        Path path = Paths.get(anexo.getCaminho());
        byte[] content = Files.readAllBytes(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(anexo.getTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNomeArquivo() + "\"")
                .body(content);
    }

    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        Optional<AnexoAnalise> optional = anexoService.buscarPorId(id);
        if (optional.isEmpty()) {
            redirectAttrs.addFlashAttribute("erro", "Anexo não encontrado.");
            return "redirect:/qualidade";
        }
        AnexoAnalise anexo = optional.get();
        Long analiseId = anexo.getAnaliseQualidade().getId();
        try {
            Files.deleteIfExists(Paths.get(anexo.getCaminho()));
        } catch (IOException ignored) {
        }
        anexoService.excluir(id);
        redirectAttrs.addFlashAttribute("sucesso", "Anexo excluído com sucesso.");
        return "redirect:/qualidade/" + analiseId + "/editar";
    }
}
