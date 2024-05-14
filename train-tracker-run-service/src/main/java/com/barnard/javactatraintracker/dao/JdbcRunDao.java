package com.barnard.javactatraintracker.dao;

import com.barnard.javactatraintracker.exception.DaoException;
import com.barnard.javactatraintracker.model.Arrival;
import com.barnard.javactatraintracker.model.TrainRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class JdbcRunDao implements RunDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTrainRun(TrainRun run) {

        String sql = "INSERT INTO train_run (run, run_date, average_prediction, arrival_time, difference_actual_average, was_late, was_faulty, prediction_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING run_id;";

        String sql2 = "UPDATE arrivals SET train_run_id = ? " +
                "WHERE ";
        String concat = "";
        String arrivalIdSql = "arrival_id = ";
        for (int i = 0; i < run.getPredictions().size(); i++) {
            concat += arrivalIdSql + run.getPredictions().get(i).getArrivalId();
            if (i == run.getPredictions().size() - 1) {
                concat += ";";
            } else {
                concat += " OR ";
            }
        }
        sql2 += concat;


        try {
            Integer runId = jdbcTemplate.queryForObject(sql, Integer.class, run.getTrainRunId(), run.getRunDate(), run.getAveragePrediction(), run.getArrivalTime(), run.getDiffActualAverage().toSeconds(), run.isWasLate(), run.isWasFaulty(), run.getCountPredictions());
            jdbcTemplate.update(sql2, runId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

    }

    @Override
    public void deleteTrainRuns() {

        String sql = "delete from train_run;";

        try {
            jdbcTemplate.update(sql);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }


}
