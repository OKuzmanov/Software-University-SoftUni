package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.models.enums.ApartmentType;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;
import softuni.exam.util.impl.FileUtilImpl;
import softuni.exam.util.impl.ValidationUtilImpl;
import softuni.exam.util.impl.XmlParserImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson getGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }


    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                String sourceDate = mappingContext.getSource();
                return LocalDate.parse(sourceDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        });

        modelMapper.addConverter(new Converter<String, ApartmentType>() {
            @Override
            public ApartmentType convert(MappingContext<String, ApartmentType> mappingContext) {
                String sourcetype = mappingContext.getSource();
                return ApartmentType.valueOf(sourcetype);
            }
        });

        return modelMapper;
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public XmlParser xmlParser() { return new XmlParserImpl();}

    @Bean
    public FileUtil getFileUtil() {
        return new FileUtilImpl();
    }
}
