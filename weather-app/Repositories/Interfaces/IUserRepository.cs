using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using weather_app.Models;

namespace weather_app.Repositories.Interfaces
{
    public interface IUserRepository
    {
        Task<User> GetUserById(int id);
        Task<List<User>> GetUsers(bool includeRoot);
        Task<bool> SaveUser(User user);
        Task<bool> RemoveUser(int id);
        Task<User> LoginUserWithEmailAndPassword(string email,string password);
        Task<bool> CheckUserFromEmail(String email);
        Task<bool> CheckUserFromUsername(String username);
        
    }
}