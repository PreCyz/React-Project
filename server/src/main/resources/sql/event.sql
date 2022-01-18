alter event carly.Eve_tbl_car_update
ON SCHEDULE EVERY 60 MINUTE
DO
UPDATE carly.car SET is_active = false where  end_date < SYSDATE()