import * as React from 'react';
import { SubmissionError } from 'redux-form'
import { submit } from 'redux-form'
import { connect } from 'react-redux';
import { Map } from 'immutable'

import { Countries } from './Countries'
import { Validator } from './FormValidator'
import FormView from './FormView'


interface Props extends React.Props<any> {
}

class FormCtrl extends React.Component<Props, {}> {
	constructor(props: any) {
		super(props);
	}

	handleSubmit = (values: any) => {
		const formData = Map<string, any>(values);
		const errors = Validator(formData);

		if (errors.size > 0) {
			const msg = 'Please check the following fields: ' + errors.toJS().join(", ");
			throw new SubmissionError({ _error: msg })
		}
		else {
			//Promise for redux-form
			const formPromise = new Promise((resolve, reject) => {

					resolve(); //trigger onSubmitSuccess on the form

					//reject(new SubmissionError({ _error: 'error' })); //trigger onSubmitFail on the form

			});

			return formPromise;
		}
	}

	render() {
		return (
			<FormView onSubmit={this.handleSubmit}/>
		);
	}
}

export default FormCtrl;