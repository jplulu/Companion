import React, {Component, Fragment} from "react";
import PropTypes from 'prop-types';
import withStyles from "@material-ui/core/styles/withStyles";

import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import LocationOn from '@material-ui/icons/LocationOn';
import noImage from "../images/no-img.png";
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";

const styles = (theme) => ({
    ...theme.spreadThis
});

const StaticProfile = (props) => {
    const {
        classes,
        userData: { profile: {firstName, lastName, age, gender, location, bio, profilePic} }
    } = props;
    const imageURL = (profilePic && profilePic.length) ? `http://localhost:8080/picture?id=${profilePic[profilePic.length-1].id}` : undefined;

    return (
        <Paper className={classes.paper}>
            <div className={classes.profile}>
                <div className="image-wrapper">
                    <img src={imageURL ? imageURL : noImage} alt="profile" className="profile-image" />
                </div>
                <hr />
                <div className="profile-details">
                    <Typography variant="h5" color="primary">{firstName} {lastName}</Typography>
                    <hr />
                    <Typography variant="body2">{gender === 0 ? '' : gender === 1 ? "Male," : "Female,"} {age > 0 ? `${age} years old` : ''}</Typography>
                    <hr/>
                    {bio && <Typography variant="h6">{bio}</Typography>}
                    <hr />
                    {location && (
                        <Fragment>
                            <LocationOn color="primary" /> <span>{location}</span>
                            <hr />
                        </Fragment>
                    )}
                    {/*Including a button to allow you to update your profile*/}
                    <Button  component={Link} to={`/users/username/survey`} style={{flex:1}}>UPDATE YOUR SURVEY RESPONSES</Button>
                </div>
            </div>
        </Paper>
    );
};

StaticProfile.propTypes = {
    userData: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(StaticProfile);