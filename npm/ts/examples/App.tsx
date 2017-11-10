import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { Provider } from 'react-redux'
import FormController from './form/FormController';

import * as bootstrap from './core/bootstrap';

const store = bootstrap.configureStore()

class App extends React.Component<any, any> {
	constructor(props:any) {
		super(props);
	}
	
	render() {
		return (
			<div>
				<div className="header">Examples</div>
				<div className="container-fluid">
					<FormController />
				</div>
				<div className="footer"></div>
			</div>
		);
	}
}

ReactDOM.render(
	<Provider store={store}>
    <App />
  </Provider>,
	document.getElementById('app')
  );