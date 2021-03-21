import './ForecastForm.css';
import { Component } from 'react';
import axios from 'axios';

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

export default class ForecastForm extends Component {

	constructor(props) {
		super(props);
		this.state = {
			city: '',
			error: ''
		};

		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		this.setState( {city: event.target.value});
	}

	handleSubmit(event) {
		if(!this.state.city) {
			return;
		}
		this.setState({error: ''});
		axios.get(`${SERVER_URL}/forecast`, {
			params: {city: this.state.city},
			headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
		}).then((response) => {
			this.props.forecastData(response.data);
		}).catch( error => {
			this.setState({error: error.response.data.message});
		})
		event.preventDefault();
		return false;
	}

	render() {
		return (
			<div className="w-forecast-form-container">
				{ this.state.error && <div className="notification is-light is-danger mt-4">{this.state.error}</div>}
				<div className="field has-text-centered">
					<label className="label is-size-4">Three Days Weather Forecast</label>
					<div className="control has-text-centered">
						<input className="input" name="city" type="text" value={this.state.city} placeholder="Enter City" onChange={this.handleChange} />
					</div>
				</div>
				<div className="field has-text-centered">
					<p className="control">
						<button type="button" onClick={this.handleSubmit} className="button is-success">
							Get Forecast
						</button>
					</p>
				</div>
			</div>
		);
	}

}