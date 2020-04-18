import {
    SET_USER,
    SET_ERRORS,
    CLEAR_ERRORS,
    LOADING_UI,
    SET_UNAUTHENTICATED,
    LOADING_USER
} from '../types';
import axios from 'axios';

export const loginUser = (userData, history) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    axios.post('http://localhost:8080/authenticate', userData)
        .then(res => {
            const jwtToken = `Bearer ${res.data.jwt}`;
            localStorage.setItem('jwtToken', jwtToken);
            axios.defaults.headers.common['Authorization'] = jwtToken;
            dispatch(getUserData(userData.username));
            dispatch({ type: CLEAR_ERRORS});
            history.push('/');
        })
        .catch(err => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        });
};

export const signupUser = (newUserData, history) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    axios.post('http://localhost:8080/user/register', newUserData)
        .then(res => {
            setAuthorizationHeader(res.data.jwt);
            dispatch(getUserData(newUserData.username));
            dispatch({ type: CLEAR_ERRORS});
            history.push('/');
        })
        .catch(err => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        });
};

export const logoutUser = () => (dispatch) => {
    localStorage.removeItem('jwtToken');
    delete axios.defaults.headers.common['Authorization'];
    dispatch({ type: SET_UNAUTHENTICATED });
};

export const getUserData = (username) => (dispatch) => {
    dispatch({ type: LOADING_USER });
    const url = `http://localhost:8080/user/${username}`;
    axios.get(url)
        .then(res => {
            dispatch({
                type: SET_USER,
                payload: res.data
            })
        })
        .catch(err => console.log(err))
};

const setAuthorizationHeader = (token) => {
    const jwtToken = `Bearer ${token}`;
    localStorage.setItem('jwtToken', jwtToken);
    axios.defaults.headers.common['Authorization'] = jwtToken;
};