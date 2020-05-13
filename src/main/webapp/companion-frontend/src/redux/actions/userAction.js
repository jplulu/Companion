import {
    SET_USER,
    SET_ERRORS,
    CLEAR_ERRORS,
    LOADING_UI,
    SET_UNAUTHENTICATED,
    LOADING_USER, LOADING_MATCHES, SET_MATCHES, DELETE_MATCH
} from '../types';
import axios from 'axios';

export const loginUser = (userData, history) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    axios.post('http://localhost:8080/authenticate', userData)
        .then(res => {
            setAuthorizationHeader(res.data.jwt);
            dispatch(getUserData(userData.username));
            dispatch(getUserMatches(userData.username));
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

export const signupUser = (newUserData) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    axios.post('http://localhost:8080/user/register', newUserData)
        .then(res => {
            setAuthorizationHeader(res.data.jwt);
            dispatch(getUserData(newUserData.username));
            dispatch({ type: CLEAR_ERRORS });
        })
        .catch(err => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        });
};

export const setupUserProfile = (username, userDetails, history) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    const url = `http://localhost:8080/user/${username}/profile`;
    axios.put(url, userDetails)
        .then(() => {
            dispatch(getUserData(username));
            dispatch({ type: CLEAR_ERRORS });
            history.push('/survey')
        })
        .catch(err => console.log(err));
};

export const setupUserSurvey = (username, surveyResults, history) => (dispatch) => {
    const url = `http://localhost:8080/user/${username}/survey`;
    axios.put(url, surveyResults)
        .then(() => {
            dispatch({ type: LOADING_MATCHES });
            const url2 = `http://localhost:8080/user/${username}/matches`;
            axios.get(url2)
                .then(res => {
                    dispatch({
                        type: SET_MATCHES,
                        payload: res.data
                    });
                    dispatch({ type: CLEAR_ERRORS });
                    history.push('/');
                })
                .catch(err => console.log(err.response))
        })
        .catch(err => console.log(err));
};

export const editUserDetails = (username, userDetails) => (dispatch) => {
    const url = `http://localhost:8080/user/${username}/profile`;
    axios.put(url, userDetails)
        .then(() => {
            dispatch(getUserData(username));
        })
        .catch(err => console.log(err));
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

export const getUserMatches = (username) => (dispatch) => {
    dispatch({ type: LOADING_MATCHES });
    const url = `http://localhost:8080/user/${username}/matches`;
    axios.get(url)
        .then(res => {
            dispatch({
                type: SET_MATCHES,
                payload: res.data
            })
        })
        .catch(err => console.log(err.response))
};

export const deleteMatch = (curUsername, idToDelete) => (dispatch) => {
    const url = `http://localhost:8080/user/${curUsername}/matches?id=${idToDelete}&status=refuse`;
    axios.put(url)
        .then(() => {
            dispatch({ type: DELETE_MATCH, payload: idToDelete })
        })
        .catch(err => console.log(err.response));
};

export const uploadImage = (username, formData) => (dispatch) => {
    dispatch({ type: LOADING_USER });
    const url = `http://localhost:8080/picture/${username}`;
    axios.post(url, formData)
        .then(res => {
            dispatch(getUserData(username));
        })
        .catch(err => console.log(err.response))
};

const setAuthorizationHeader = (token) => {
    const jwtToken = `Bearer ${token}`;
    localStorage.setItem('jwtToken', jwtToken);
    axios.defaults.headers.common['Authorization'] = jwtToken;
};