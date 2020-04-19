import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css'
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
import createMuiTheme from '@material-ui/core/styles/createMuiTheme'
import themeFile from './util/theme'
import jwtDecode from 'jwt-decode'
import axios from 'axios'
// Redux
import {Provider} from 'react-redux'
import store from './redux/store'
import { SET_AUTHENTICATED } from "./redux/types";
import {logoutUser, getUserData, getUserMatches} from "./redux/actions/userAction";
// Components
import NavigationBar from "./components/NavigationBar";
import AuthRoute from './util/AuthRoute'
// Pages
import home from './pages/home'
import login from './pages/login'
import signup from './pages/signup'
import user from './pages/user'

const theme = createMuiTheme(themeFile);

const token = localStorage.jwtToken;
if(token) {
    const decodedToken = jwtDecode(token);
    if(decodedToken.exp * 1000 < Date.now()) {
        store.dispatch(logoutUser());
        window.location.href = 'http://localhost:3000/login';
    } else {
        store.dispatch({ type: SET_AUTHENTICATED });
        axios.defaults.headers.common['Authorization'] = token;
        store.dispatch(getUserData(decodedToken.sub));
        store.dispatch(getUserMatches(decodedToken.sub));
    }
}

function App() {
  return (
    <MuiThemeProvider theme={theme}>
        <Provider store={store}>
            <Router>
                <NavigationBar/>
                <div className="container">
                    <Switch>
                        <Route path="/" exact component={home}/>
                        <AuthRoute path="/login" exact component={login} />
                        <AuthRoute path="/signup" exact component={signup} />
                        <Route path="/users/:username" exact component={user}/>
                    </Switch>
                </div>
            </Router>
        </Provider>
    </MuiThemeProvider>
  );
}

export default App;
