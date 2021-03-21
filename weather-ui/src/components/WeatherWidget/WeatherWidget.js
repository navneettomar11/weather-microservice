import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCloud, faCloudSun, faSun, faCloudRain } from '@fortawesome/free-solid-svg-icons'

const WeatherWidget = (props) => {

	return (
		<div className="tile is-parent">
			<article className="tile is-child box">
			<p className="title has-text-centered">{props.weather.datetime}</p>
			<p className="subtitle has-text-centered">
				{
				props.weather.condition.condition === 'RAIN' ? 
					<span><FontAwesomeIcon icon={faCloudRain} /> <span className="tag">Rain is predicted. Please carry umberella.</span></span>:
					props.weather.condition.condition === 'SUNNY' ?
					<span><FontAwesomeIcon icon={faSun}/> :	?
					<span className="tag">Too Sunny . Please carry suns cream lotion.</span></span> :
					props.weather.condition.condition === 'CLEAR' ?
					<span><FontAwesomeIcon icon={faCloudSun}/> <span className="tag">{props.weather.condition.description}</span></span>:
					<span><FontAwesomeIcon icon={faCloud}/> <span className="tag">No worries</span></span>
				}
			</p>
		
			<table className="table is-fullwidth">
				<tbody>
					<tr>
						<td className="has-text-weight-semibold">Current Temperature</td>
						<td>{props.weather.currTemp}&#8451;</td>
					</tr>
					<tr>
						<td className="has-text-weight-semibold">Min Temperature</td>
						<td>{props.weather.minTemp}&#8451;</td>
					</tr>
					<tr>
						<td className="has-text-weight-semibold">Max Temperature</td>
						<td>{props.weather.maxTemp}&#8451;</td>
					</tr>
				</tbody>
			</table>
			</article>
		</div>
	);
}

export default WeatherWidget;