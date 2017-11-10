import { List, Map } from 'immutable'

const validateObject = (s: any): boolean => s && Object.keys(s).length > 0
const validateString = (s: string): boolean => s && scalajs.Validator.minLength(s, 1)
const validateDate = (s: string): boolean => s &&  scalajs.Validator.isValidDate(s)
const validatePhone = (s: string): boolean => s && scalajs.Validator.isValidPhone(s)
const validateEmail = (s: string): boolean => s && scalajs.Validator.isValidEmail(s)

export const Validator = (formData: Map<any, any>): List<string> => {
    let errors: List<any> = List<any>();

    if (!validateObject(formData.get('nationality'))) {
        errors = errors.push('Nationality');
    }

    if (!validateEmail(formData.get('email'))) {
    	errors = errors.push('E-Mail');
    }
    if (!validatePhone(formData.get('phone'))) {
        errors = errors.push('Phone');
    }

    if (!validateString(formData.get('name'))) {
        errors = errors.push('Name');
    }
    if (!validateDate(formData.get('birthDate'))) {
        errors = errors.push('Birth date');
    }

    return errors;
}
