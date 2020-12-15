using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using weather_app.Models;

namespace weather_app.Repositories.Interfaces
{
    public interface IUserRepository
    {
        Task<User> GetUserById(int id);
        Task<List<User>> GetUsers(String term);
        Task<bool> SaveUser(User user);
        Task<bool> RemoveUser(User user);
        Task<User> LoginUserWithEmailAndPassword(string email,string password);
        
    }
}