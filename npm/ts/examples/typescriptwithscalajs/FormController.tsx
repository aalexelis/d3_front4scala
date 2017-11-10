import * as React from 'react';
import { SubmissionError } from 'redux-form'
import { submit } from 'redux-form'
import { connect } from 'react-redux';
import { Map } from 'immutable'

import { Api, Request } from 'lib/Api'
import { Validator } from './FormValidator'
import FormView from './FormView'

interface Props extends React.Props<any> {
}

interface State {
	success: boolean
}

class FormCtrl extends React.Component<Props, State> {
	constructor(props: any) {
		super(props);
		this.state = {
			success: false,
		}
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
				const request: Request = {
					url: jsRoutes.controllers.ScalaJsTypescriptController.submitForm().url,
					payload: {
						name: formData.get('name'),
						birthDate: formData.get('birthDate'),
						phone: formData.get('phone'),
						email: formData.get('email'),
						nationality: formData.get('nationality').id
					}
				}
				Api.post(request).then(() => {
					this.setState({
						success: true
					})
					resolve(); //trigger onSubmitSuccess on the form
				}).catch((error: any) => {
					reject(new SubmissionError({ _error: error })); //trigger onSubmitFail on the form
				});
			});

			return formPromise;
		}
	}

	render() {
		return (
			<FormView onSubmit={this.handleSubmit} success={this.state.success}/>
		);
	}
}

export default FormCtrl;