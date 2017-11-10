import { List, Map } from 'immutable'

const validateObject = (s: any): boolean => s && Object.keys(s).length > 0
const validateString = (s: string): boolean => s && s.length > 0
const validateDate = (s: string): boolean => {
	if (!(s && s.length > 0)) {
		return false;
	}
	try {
		const d = new Date(s).toISOString()
	} catch (e) {
		return false
	}
	return true;
}

const validatePhone = (s: string): boolean => {
	if (!(s && s.length > 0)) {
		return false
	}
	//sanitization
	//removes (0), ' ', '(' abd ')'
	const phone = s.replace("(0)", "").replace(" ", "").replace("(", "").replace(")", "");

	//checks format +81-XXXXXXXX 
	//first part : +CounryCode- (surrounding + and - are mandatory), between 1-3 digits for country code
	//second part: at least 8 digits (ignore hyphen) 
	return /^(\+\d{1,3})(\-)([\d|\-]{8,})$/.test(phone);
}

const validateEmail = (email: string): boolean => email && /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email)

export const Validator = (formData: Map<any, any>): List<string> => {
    let errors: List<any> = List<any>();

    if (!validateObject(formData.get('currentLocation'))) {
        errors = errors.push('Current location');
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
