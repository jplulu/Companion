import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css'
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
import createMuiTheme from '@material-ui/core/styles/createMuiTheme'
// Components
import NavigationBar from "./components/NavigationBar";
// Pages
import home from './pages/home'
import login from './pages/login'
import signup from './pages/signup'

const theme = createMuiTheme({
    palette: {
        primary: {
            light: '#33c9dc',
            main: '#00bcd4',
            dark: '#008394',
            contrastText: '#fff'
        },
        secondary: {
            light: '#ff6333',
            main: '#ff3d00',
            dark: '#b22a00',
            contrastText: '#fff'
        }
    },
    typography: {
        useNextVariants: true
    }
});

function App() {
  return (
    <MuiThemeProvider theme={theme}>
        <div className="App">
            <Router>
                <NavigationBar/>
                <div className="container">
                    <Switch>
                        <Route path="/" exact component={home}/>
                        <Route path="/login" exact component={login}/>
                        <Route path="/signup" exact component={signup}/>
                    </Switch>
                </div>
            </Router>
        </div>
    </MuiThemeProvider>
  );
}

export default App;
