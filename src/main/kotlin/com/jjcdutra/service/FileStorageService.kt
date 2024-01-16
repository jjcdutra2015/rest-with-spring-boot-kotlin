package com.jjcdutra.service

import com.jjcdutra.config.FileStorageConfig
import com.jjcdutra.exceptions.FileStorageException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService(
    private val fileStorageConfig: FileStorageConfig
) {

    private val fileStorageLocation: Path

    init {
        fileStorageLocation = Paths.get(fileStorageConfig.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectory(fileStorageLocation)
        } catch (e: Exception) {
            throw FileStorageException("Could not create the directory where the uploaded files will be stored", e)
        }
    }

    fun storageFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try {
            if (fileName.contains(".."))
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName.")
            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (e: Exception) {
            throw FileStorageException("Could not store file $fileName. Please try again!", e)
        }
    }
}