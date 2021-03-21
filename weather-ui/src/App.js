import React from 'react';
import './App.css';
import {Navbar, ForecastForm, WeatherWidget} from './components';

export default class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      weathers: []
    };
    this.forecastWeatherData = this.forecastWeatherData.bind(this)
  }

  forecastWeatherData(weatherData) {
     this.setState({weathers: weatherData});
  }

  render() {
    const weathers = this.state.weathers;
    return (
      <div className="wrapper">
        <Navbar/>
        <main className="container is-widescreen is-fullhd">
          <ForecastForm forecastData={this.forecastWeatherData} />
          <div className="tile is-ancestor mt-1">
            {weathers  && weathers.map(weather => <WeatherWidget key={weather.datetime} weather={weather} />)}
          </div>
        </main>
      </div>
    );
  }
}
