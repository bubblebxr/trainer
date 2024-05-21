import api from './request';

export const getStation = () => {
    return api.get('/station');
};

export const getSearchResult = (startCity, arriveCity, date, isGD, sortType, seatType, isHide) => {
    const queryString = `?is_GD=${isGD}&sort_type=${sortType}&seat_type=${seatType}&isHide=${isHide}`;
    return api.post(`/trains/${startCity}/${arriveCity}/${date}${queryString}`);
};

export const getPlaces = () => {
    return api.get('/getPlaces');
};

export const getHotel = (arrive_station, arrive_date, Ideparture_date, sort_type) => {
    const queryString = `?sort_type=${sort_type}`;
    return api.get(`/hotel/${arrive_station}/${arrive_date}/${Ideparture_date}${queryString}`);
};