import {
    SET_USER, SET_AUTHENTICATED, SET_UNAUTHENTICATED, LOADING_USER, SET_MATCHES, LOADING_MATCHES, DELETE_MATCH
} from '../types';

const initialState = {
    authenticated: false,
    loading: false,
    loadingMatches: false,
    id: 0,
    username: '',
    profile: {},
    matches: []
};

export default function(state = initialState, action) {
    switch (action.type) {
        case SET_AUTHENTICATED:
            return {
                ...state,
                authenticated: true
            };
        case SET_UNAUTHENTICATED:
            return initialState;
        case SET_USER:
            return {
                ...state,
                authenticated: true,
                loading: false,
                ...action.payload
            };
        case SET_MATCHES:
            return {
                ...state,
                loadingMatches: false,
                matches: action.payload
            };
        case DELETE_MATCH:
            let index = state.matches.findIndex(match => match.id === action.payload);
            state.matches.splice(index, 1);
            return {
                ...state
            };
        case LOADING_USER:
            return {
                ...state,
                loading: true
            };
        case LOADING_MATCHES:
            return {
                ...state,
                loadingMatches: true
            };
        default:
            return state;
    }
}