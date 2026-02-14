package org.anirudh.spring.weatherserver.service;


import com.anirudh.grpc.WeatherRequest;
import com.anirudh.grpc.WeatherResponse;
import io.grpc.stub.StreamObserver;
import org.anirudh.spring.weatherserver.entity.Weather;
import org.anirudh.spring.weatherserver.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.stereotype.Repository;

@GrpcService
public class WeatherServiceImpl extends com.anirudh.grpc.WeatherServiceGrpc.WeatherServiceImplBase {

    private final WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void getWeatherByState(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {

        String stateSymbol = request.getState();
        Weather weatherEntityFromDatabase = weatherRepository.findByState(stateSymbol);

        WeatherResponse weatherResponse = WeatherResponse.newBuilder()
                .setState(weatherEntityFromDatabase.getState())
                .setTemperature(weatherEntityFromDatabase.getTemperature())
                .setTimestamp(weatherEntityFromDatabase.getTimestamp().toString())
                .build();

        responseObserver.onNext(weatherResponse);
        responseObserver.onCompleted();
        // Response Builder of gRPC
    }
}
