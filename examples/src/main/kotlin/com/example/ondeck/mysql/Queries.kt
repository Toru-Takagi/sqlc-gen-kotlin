// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.25.0

package com.example.ondeck.mysql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import java.time.Instant

interface Queries {
  @Throws(SQLException::class)
  fun createCity(name: String, slug: String)
  
  @Throws(SQLException::class)
  fun createVenue(
      slug: String,
      name: String,
      city: String,
      spotifyPlaylist: String,
      status: VenueStatus,
      statuses: String?,
      tags: String?): Long
  
  @Throws(SQLException::class)
  fun deleteVenue(slug: String, slug_2: String)
  
  @Throws(SQLException::class)
  fun getCity(slug: String): City?
  
  @Throws(SQLException::class)
  fun getVenue(slug: String, city: String): Venue?
  
  @Throws(SQLException::class)
  fun listCities(): List<City>
  
  @Throws(SQLException::class)
  fun listVenues(city: String): List<Venue>
  
  @Throws(SQLException::class)
  fun updateCityName(name: String, slug: String)
  
  @Throws(SQLException::class)
  fun updateVenueName(name: String, slug: String)
  
  @Throws(SQLException::class)
  fun venueCountByCity(): List<VenueCountByCityRow>
  
}

