'use strict';
console.log('Loading hello world function');
const axios = require('axios')
 
exports.handler = async (event) => {
    let latitude = 0
    let longitude = 0
    let responseCode = 200;
    console.log("request: " + JSON.stringify(event));
    
    if (event.queryStringParameters && event.queryStringParameters.latitude) {
        console.log("Received latitude: " + event.queryStringParameters.latitude);
        latitude = parseFloat(event.queryStringParameters.latitude);
    }
    
    if (event.queryStringParameters && event.queryStringParameters.longitude) {
        console.log("Received longitude: " + event.queryStringParameters.longitude);
        longitude = parseFloat(event.queryStringParameters.longitude);
    }
    
    const weather_url = `https://api.weather.gov/points/${latitude},${longitude}`;
    const location = await axios.get(weather_url).then(res => res.data);
    
    const forecast = await axios.get(location.properties.forecast).then(res => res.data)
    
    const periods = forecast.properties.periods
    
    const weather = periods[periods.length - 1].detailedForecast
    

    console.log(location.properties.forecast)
    let responseBody = {
        message: forecast.properties,
        weather: weather
    };
    
    // The output from a Lambda proxy integration must be 
    // in the following JSON object. The 'headers' property 
    // is for custom response headers in addition to standard 
    // ones. The 'body' property  must be a JSON string. For 
    // base64-encoded payload, you must also set the 'isBase64Encoded'
    // property to 'true'.
    let response = {
        statusCode: responseCode,
        headers: {
            "x-custom-header" : "my custom header value"
        },
        body: JSON.stringify(responseBody)
    };
    console.log("response: " + JSON.stringify(response))
    return response;
};