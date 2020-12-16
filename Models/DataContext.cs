using System;
using Microsoft.EntityFrameworkCore;

namespace weather_app.Models
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options){}

        public DbSet<User> User{get;set;}
        public DbSet<Location> Location{get;set;}
    }
}
