import React, {Component} from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import noImage from '../images/no-img.png'
import { Link } from "react-router-dom";
import DeleteMatch from "./DeleteMatch";

// MUI
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from "@material-ui/core/Typography";

const styles = {
    card: {
        position: 'relative',
        display: 'flex',
        marginBottom: 20,
    },
    image: {
        minWidth: 200,
    },
    content:{
        padding: 25,
        objectFit: 'cover'
    }
};

class Match extends Component {
    render() {
        const { classes, curUsername, match : {id, username, profile: {firstName, lastName, age, bio, location, profilePic}}} = this.props;
        const imageURL = profilePic[0] ? `http://localhost:8080/picture?id=${profilePic[profilePic.length - 1].id}` : undefined;
        return (
            <Card className={classes.card}>
                <CardMedia
                    image={imageURL ? imageURL : noImage}
                    title="Profile picture" className={classes.image}/>
                    <CardContent className={classes.content}>
                        <Typography variant="h5" color="primary" component={Link} to={`/users/${username}`}>{firstName}</Typography>
                        <DeleteMatch curUsername={curUsername} idToDelete={id}/>
                        <Typography variant="body1">{bio}</Typography>
                        <Typography variant="body2" color="textSecondary">{location}</Typography>
                    </CardContent>
            </Card>
        )
    }
}


export default withStyles(styles)(Match)