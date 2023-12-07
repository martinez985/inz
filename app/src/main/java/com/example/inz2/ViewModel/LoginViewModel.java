package com.example.inz2.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.dto.LoginBody;
import com.example.inz2.dto.LoginResponse;
import com.example.inz2.repository.Repository;


public class LoginViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginErrorLiveData = new MutableLiveData<>();

    public LoginViewModel() {
        this.repository = new Repository();
    }

    public void login(String username, String password) {
        LoginBody loginBody = new LoginBody(username, password);
        repository.loginRemote(loginBody, new Repository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                loginResponseLiveData.setValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                loginErrorLiveData.setValue(true);
            }
        });
    }

    public MutableLiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }
    public MutableLiveData<Boolean> getLoginErrorLiveData() {
        return loginErrorLiveData;
    }
}
