package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.CountryModel;
import xyz.toway.emarket.model.LanguageModel;
import xyz.toway.emarket.service.GeneralService;

@CrossOrigin
@Controller
@AllArgsConstructor
public class GeneralController {

    private final GeneralService generalService;

    @QueryMapping("languages")
    public Flux<LanguageModel> getLanguages() {
        return generalService.getLanguages();
    }

    @QueryMapping("countries")
    public Flux<CountryModel> getCountries() {
        return generalService.getCountries();
    }
}
