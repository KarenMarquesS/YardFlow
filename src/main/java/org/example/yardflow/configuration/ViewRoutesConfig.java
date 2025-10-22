package org.example.yardflow.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewRoutesConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cadastros/moto").setViewName("cadastros/moto");
        registry.addViewController("/cadastros/patio").setViewName("cadastros/patio");
        registry.addViewController("/cadastros/usuario").setViewName("cadastros/usuario");
        registry.addViewController("/cadastros/yardflow").setViewName("cadastros/yardflow");
        registry.addViewController("/consultas/listaMoto").setViewName("consultas/listaMoto");
        registry.addViewController("/consultas/localizarMoto").setViewName("consultas/localizarMoto");
        registry.addViewController("/consultas/detalhesMoto").setViewName("consultas/detalhesMoto");
    }
}


