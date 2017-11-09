import * as React from 'react';
import { Field, reduxForm } from 'redux-form';
import { SelectFormField } from 'lib/components/SelectFormField';
import { Countries } from './Countries'

interface Props {
    onSubmit: any;
    handleSubmit?: any;
    pristine?: any;
    submitting?: any,
    error?: any
}
class FormView extends React.Component<Props, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        const countries = Countries.map((v: any) => {
            return { key: v.id, id: v.id, value: v.id, label: v.label };
        });
        const { error, handleSubmit, pristine, submitting } = this.props
        return (
            <form onSubmit={handleSubmit}>
                {error && <strong style={{ color: 'red' }}>{error}</strong>}
                <div className="form-group required">
                    <label className="control-label" htmlFor="name">Name</label>
                    <Field component="input" type="text" required={true} name="name" className="form-control form-control input-sm" placeholder="(eg. Akiko Takeda)" />
                </div>
                <div className="form-group required">
                    <label className="control-label" htmlFor="birthDate">Birthdate</label>
                    <Field component="input" type="date" required={true} name="birthDate" className="form-control form-control input-sm" placeholder="(eg. 1984-12-30)" />
                </div>
                <div className="form-group required">
                    <label className="control-label" htmlFor="email">Email</label>
                    <Field component="input" type="email" required={true} name="email" className="form-control form-control input-sm" placeholder="(eg. akika.takeda@email.com)" />
                </div>

                <div className="form-group required">
                    <label className="control-label" htmlFor="phone">Phone</label>
                    <Field component="input" type="text" required={true} name="phone" className="form-control form-control input-sm" placeholder="(eg. +81-80111111)" />
                    <span className="help-block">Number format: +CountryCode-XXXXX (e.g. +1-6507668756)</span>
                </div>

                <div className="form-group required">
                    <label className="control-label" htmlFor="currentLocation">Current location</label>
                    <Field
                        required={true}
                        name='currentLocation'
                        options={countries}
                        component={SelectFormField}
                        placeholder='Select or type...'
                    />
                </div>
                <div className="form-group">
                    <button type="submit">Submit</button>
                </div>
            </form>
        );
    }
}

export default reduxForm({
    form: 'myform',
})<any>(FormView);