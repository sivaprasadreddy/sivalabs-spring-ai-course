package com.sivalabs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class AiConfig {
    private static final Logger log = LoggerFactory.getLogger(AiConfig.class);

    @Value("classpath:/data/about-siva.md")
    private Resource biodataFile;

    @Value("classpath:/data/few-things-i-learned-the-hardway-in-15-years-of-my-career.md")
    private Resource careerLessonsFile;

    /*@Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
       return SimpleVectorStore.builder(embeddingModel).build();
    }*/

    @Bean
    ApplicationRunner applicationRunner(VectorStore vectorStore) {
        return args -> {
            loadDocument(vectorStore, biodataFile);
            loadDocument(vectorStore, careerLessonsFile);
        };
    }

    private void loadDocument(VectorStore vectorStore, Resource resource) {
        log.info("Loading document {} into vector store", resource.getFilename());
         //For loading text docs
        //DocumentReader documentReader = new TextReader(resource);

        //For loading PDF docs
        //DocumentReader documentReader = new PagePdfDocumentReader(resource);

        //For loading PDF, DOC/DOCX, PPT/PPTX, and HTML docs
        //DocumentReader documentReader = new TikaDocumentReader(sivaBiodataFile);

        DocumentReader documentReader = new MarkdownDocumentReader(resource, MarkdownDocumentReaderConfig.defaultConfig());

        List<Document> documents = documentReader.get();
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> splitDocuments = textSplitter.apply(documents);
        vectorStore.accept(splitDocuments);
        log.info("Document {} loaded into vector store", resource.getFilename());
    }

}
