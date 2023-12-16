package base.backend.Base.Project.controllers;

import base.backend.Base.Project.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@Tag(name = "Files", description = "Operations for interacting with files")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @Operation(summary = "Upload file")
  @PostMapping(
    value = "/uploadFile",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<Boolean> addFile(
    @RequestPart(value = "file") @Parameter(
      content = @Content(
        mediaType = "multipart/form-data",
        schema = @Schema(type = "string", format = "binary")
      )
    ) MultipartFile file
  ) {
    if (fileService.addFile(file)) {
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.badRequest().body(false);
    }
  }

  @Operation(summary = "Remove file")
  @DeleteMapping("/deleteFile")
  public ResponseEntity<Boolean> removeFile(
    @RequestParam @Parameter(description = "File name") String fileName
  ) {
    if (fileService.removeFile(fileName)) {
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.badRequest().body(false);
    }
  }

  @Operation(summary = "Find file")
  @GetMapping("/{fileName:.+}")
  public ResponseEntity<InputStreamResource> serveFile(
    @PathVariable String fileName
  ) {
    try {
      Resource resource = fileService.loadFile(fileName);

      if (resource.exists()) {
        InputStream inputStream = resource.getInputStream();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("inline", resource.getFilename());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(
          new InputStreamResource(inputStream),
          headers,
          HttpStatus.OK
        );
      } else {
        return ResponseEntity.badRequest().build();
      }
    } catch (IOException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
