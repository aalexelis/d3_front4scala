import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { Provider } from 'react-redux'
import TypesriptOnly from './typescript/FormController';
import Typescriptwithscalajs from './typescriptwithscalajs/FormController';

import * as bootstrap from './common/bootstrap';

const store = bootstrap.configureStore()

class App extends React.Component<any, any> {
	constructor(props: any) {
		super(props);
	}

	render() {
		return (
			<div>
				<div className="container-fluid">
					<div className="row">
						<div className="col-xs-6">
							<strong>Typescript</strong>
							<TypesriptOnly />
						</div>
						<div className="col-xs-6">
							<strong>Typescript & Scalajs</strong>
							<Typescriptwithscalajs />
						</div>
					</div>
				</div>
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