package com.jjcdutra.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
class FileStorageConfig(val uploadDir: String = "")