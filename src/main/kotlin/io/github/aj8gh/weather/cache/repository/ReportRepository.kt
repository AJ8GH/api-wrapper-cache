package io.github.aj8gh.weather.cache.repository

import io.github.aj8gh.weather.cache.model.Report
import org.springframework.data.repository.CrudRepository

interface ReportRepository : CrudRepository<Report, String>
