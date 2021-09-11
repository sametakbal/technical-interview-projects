using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using weather_app.Filters;
using weather_app.Models;
using weather_app.Models.Dtos;
using weather_app.Repositories.Interfaces;

namespace weather_app.Controllers
{
    [UserFilter]
    public class UserController : Controller
    {
        private readonly IUserRepository _repo;

        public UserController(IUserRepository repo)
        {
            _repo = repo;
        }
#nullable enable
        [HttpGet]
        public async Task<IActionResult> Index(string? term)
        {
            return View(await _repo.GetUsers(false));
        }

        [HttpGet]
        public async Task<IActionResult> UserPage(int? id)
        {
            if (id != null)
            {
                User user = await _repo.GetUserById(id.Value);
                if (user != null)
                {
                    return View(new RegisterDto
                    {
                        Id = user.Id,
                        FullName = user.FullName,
                        UserName = user.UserName,
                        Email = user.Email,
                        Password = user.Password,
                        IsAdmin = user.IsAdmin
                    });
                }
            }
            return View(new RegisterDto());
        }

        [HttpPost]
        public async Task<IActionResult> SaveUser(RegisterDto RegisterDto)
        {
            if (RegisterDto.Id == 0)
            {
                if (!await _repo.CheckUserFromEmail(RegisterDto.Email))
                {
                    RegisterDto.ErrorMessage = "This Email is already in use!";
                    return PartialView("UserPage", RegisterDto);
                }
                else if (!await _repo.CheckUserFromUsername(RegisterDto.UserName))
                {
                    RegisterDto.ErrorMessage = "This Username is already in use!";
                    return PartialView("UserPage", RegisterDto);
                }
            }
            await _repo.SaveUser(new User
            {
                Id = RegisterDto.Id,
                FullName = RegisterDto.FullName,
                Email = RegisterDto.Email,
                UserName = RegisterDto.UserName,
                Password = RegisterDto.Password,
                IsAdmin = RegisterDto.IsAdmin
            });
            return Redirect("Index");
        }


        [HttpGet]
        public async Task<IActionResult> DeleteUser(int id)
        {
            return Ok(await _repo.RemoveUser(id));
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
