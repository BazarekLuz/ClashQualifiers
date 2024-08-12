package dev.bazarski.clashqualifiers.props.SearchProps;

import dev.bazarski.clashqualifiers.records.Account;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public abstract class SearchProperties {

    protected List<Account> accounts;
    protected MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public MultiValueMap<String, String> getParams() {
        return params;
    }
}
