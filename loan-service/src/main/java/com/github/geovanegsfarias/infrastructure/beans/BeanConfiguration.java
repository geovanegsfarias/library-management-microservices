package com.github.geovanegsfarias.infrastructure.beans;

import com.github.geovanegsfarias.core.gateway.*;
import com.github.geovanegsfarias.core.usecases.*;
import com.github.geovanegsfarias.infrastructure.client.BookClient;
import com.github.geovanegsfarias.infrastructure.gateway.*;
import com.github.geovanegsfarias.infrastructure.mapper.LoanEntityMapper;
import com.github.geovanegsfarias.infrastructure.mapper.LoanMapper;
import com.github.geovanegsfarias.infrastructure.mapper.UserEntityMapper;
import com.github.geovanegsfarias.infrastructure.persistence.LoanRepository;
import com.github.geovanegsfarias.infrastructure.persistence.UserRepository;
import com.github.geovanegsfarias.infrastructure.producer.NotificationProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public LoanGateway loanGateway(LoanRepository loanRepository, LoanEntityMapper loanEntityMapper) {
        return new LoanRepositoryGateway(loanRepository, loanEntityMapper);
    }

    @Bean
    public UserGateway userGateway(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        return new UserRepositoryGateway(userRepository, userEntityMapper);
    }

    @Bean
    public BookClientGateway bookClientGateway(BookClient bookClient, ApiKeyProperties apiKeyProperties) {
        return new BookClientGatewayImpl(bookClient, apiKeyProperties);
    }

    @Bean
    public PasswordEncoderGateway passwordEncoderGateway(PasswordEncoder passwordEncoder) {
        return new PasswordEncoderGatewayImpl(passwordEncoder);
    }

    @Bean
    public NotificationGateway notificationGateway(NotificationProducer notificationProducer, LoanMapper loanMapper) {
        return new NotificationGatewayImpl(notificationProducer, loanMapper);
    }

    @Bean
    public FindAllLoansUseCase findAllLoansUseCase(LoanGateway loanGateway) {
        return new FindAllLoansUseCase(loanGateway);
    }

    @Bean
    public FindLoanByIdUseCase findLoanByIdUseCase(LoanGateway loanGateway) {
        return new FindLoanByIdUseCase(loanGateway);
    }

    @Bean
    public FindUserByEmailUseCase findUserByEmailUseCase(UserGateway userGateway) {
        return new FindUserByEmailUseCase(userGateway);
    }

    @Bean
    public ProcessOverdueLoansUseCase processOverdueLoansUseCase(LoanGateway loanGateway, NotificationGateway notificationGateway) {
        return new ProcessOverdueLoansUseCase(loanGateway, notificationGateway);
    }

    @Bean
    public ReturnLoanUseCase returnLoanUseCase(LoanGateway loanGateway, BookClientGateway bookClientGateway, FindLoanByIdUseCase findLoanByIdUseCase) {
        return new ReturnLoanUseCase(loanGateway, bookClientGateway, findLoanByIdUseCase);
    }

    @Bean
    public SaveLoanUseCase saveLoanUseCase(LoanGateway loanGateway, FindUserByEmailUseCase findUserByEmailUseCase, BookClientGateway bookClientGateway) {
        return new SaveLoanUseCase(loanGateway, bookClientGateway, findUserByEmailUseCase);
    }

    @Bean
    public SaveUserUseCase saveUserUseCase(UserGateway userGateway, PasswordEncoderGateway passwordEncoderGateway) {
        return new SaveUserUseCase(userGateway, passwordEncoderGateway);
    }
}