package xyz.toway.emarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.CountryModel;
import xyz.toway.emarket.model.CountryModelRepository;
import xyz.toway.emarket.model.LanguageModel;
import xyz.toway.emarket.model.LanguageModelRepository;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GeneralService {

    private final LanguageModelRepository languageRepository;
    private final CountryModelRepository countryRepository;

    public Flux<LanguageModel> getLanguages() {
        return languageRepository.findAll().sort(Comparator.comparing(LanguageModel::getIsDefault).reversed());
    }

    public Flux<CountryModel> getCountries() {
        return countryRepository.findAll().sort(Comparator.comparing(CountryModel::getName));
    }
}
