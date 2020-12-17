using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using weather_app.Models;
using weather_app.Repositories.Interfaces;

namespace weather_app.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly DataContext _context;

        public UserRepository(DataContext context)
        {
            _context = context;
        }

        public async Task<User> GetUserById(int id)
        {
            return await _context.User.FirstOrDefaultAsync(u => u.UserName != "root" && u.Id == id);
        }

        public async Task<bool> CheckUserFromEmail(string email)
        {
            return await _context.User.FirstOrDefaultAsync(u => u.Email.Equals(email)) == null;
        }

        public async Task<bool> CheckUserFromUsername(string username)
        {
            return await _context.User.FirstOrDefaultAsync(u => u.UserName.Equals(username)) == null;
        }

        public async Task<List<User>> GetUsers(bool includeRoot)
        {
            return includeRoot ? await _context.User.ToListAsync() : await _context.User.Where(u => u.UserName != "root").ToListAsync();
        }

        public async Task<User> LoginUserWithEmailAndPassword(string email, string password)
        {
            var user = await _context.User.FirstOrDefaultAsync(u =>
            (u.Email == email || u.UserName == email) && u.Password == password);
            if (user != null)
            {
                user.LastLogin = DateTime.Now;
                await SaveUser(user);
            }
            return user;
        }

        public async Task<bool> RemoveUser(int id)
        {
            _context.User.Remove(await GetUserById(id));
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }

        public async Task<bool> SaveUser(User user)
        {
            if (user.Id == 0)
            {
                await _context.User.AddAsync(user);
            }
            else
            {
                _context.User.Update(user);
            }
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }
    }
}