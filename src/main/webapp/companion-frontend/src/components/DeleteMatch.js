import React, { Component, Fragment } from "react";
import PropTypes from 'prop-types'
import withStyles from "@material-ui/core/styles/withStyles";
// MUI
import Button from "@material-ui/core/Button";
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
// Redux
import { connect } from 'react-redux';
import { deleteMatch } from '../redux/actions/userAction'

const styles = {
    deleteButton: {
        position: 'absolute',
        left: '90%',
        top: '10%'
    }
};

class DeleteMatch extends Component {
    state = {
        open: false
    };

    handleOpen = () => {
        this.setState({
            open: true
        })
    };

    handleClose = () => {
        this.setState({
            open: false
        })
    };

    deleteMatch = () => {
        this.props.deleteMatch(this.props.curUsername, this.props.idToDelete);
        this.setState({
            open: false
        })
    };

    render() {
        const { classes } = this.props;
        return (
            <Fragment>
                <Tooltip title="Refuse Match" placement="top">
                    <IconButton onClick={this.handleOpen} className={classes.deleteButton}>
                        <DeleteOutline color="secondary"/>
                    </IconButton>
                </Tooltip>
                <Dialog open={this.state.open}
                        onClose={this.handleClose}
                        fullWidth
                        maxWidth="sm">
                    <DialogTitle>
                        Are you sure you want to refuse this match?
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Cancel
                        </Button>
                        <Button onClick={this.deleteMatch} color="secondary">
                            Confirm
                        </Button>
                    </DialogActions>
                </Dialog>
            </Fragment>
        )
    }
}

DeleteMatch.propTypes = {
    deleteMatch: PropTypes.func.isRequired,
    classes: PropTypes.object.isRequired,
    curUsername: PropTypes.string.isRequired,
    idToDelete: PropTypes.number.isRequired
};

export default connect(null, { deleteMatch })(withStyles(styles)(DeleteMatch));