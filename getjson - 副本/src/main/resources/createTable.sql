create external table if not exists T_NCP_STAT(
  I_MPTYPE INT,
  I_MPTYPE_VALUE string,
  I_PROVINCE_ID INT,
  I_CITY_ID INT,
  I_COVER_TYPE string,
  n_lt_effective_ratio float,
  n_yd_effective_ratio float,
  n_dx_effective_ratio float,
  N_TOTAL_SCORE float
)
PARTITIONED BY (month string)
row format delimited fields terminated by ','
STORED AS textfile
location '/catt/data/ncp/T_NCP_STAT'