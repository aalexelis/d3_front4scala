import {Store, createStore, applyMiddleware} from 'redux';
import createSagaMiddleware from 'redux-saga'
import {fork} from 'redux-saga/effects';
import { combineReducers } from 'redux'
import { reducer as formReducer } from 'redux-form'
import { routerReducer } from 'react-router-redux'

export function configureStore() : Store<any> {
    const reducer = combineReducers({
        form: formReducer
      });

    const store = createStore(reducer, (<any>window).__REDUX_DEVTOOLS_EXTENSION__ && (<any>window).__REDUX_DEVTOOLS_EXTENSION__());

    return store;
}
