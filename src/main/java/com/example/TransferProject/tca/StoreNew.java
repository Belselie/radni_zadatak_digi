package com.example.TransferProject.tca;

import com.example.TransferProject.tca.actions.TransactionActionNew;
import com.example.TransferProject.tca.reducer.TransactionReducer;
import com.example.TransferProject.tca.reducer.TransactionReducerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class StoreNew {
    public TransactionState stateNew= TransactionState.init();
    public TransactionReducer transactionReducer;

    public StoreNew(@Autowired TransactionReducer transactionReducer) {
        this.transactionReducer = transactionReducer;
    }

    public TransactionState send(TransactionActionNew action) {
        TransactionReducerResult result;
        while (action != null) {
            result = transactionReducer.reduce(stateNew, action);
            stateNew = result.state();
            if (result.effect() != null){
                action = result.effect().run();
            }
            else {
                action = null;
            }
        }
        return stateNew;
    }
}
