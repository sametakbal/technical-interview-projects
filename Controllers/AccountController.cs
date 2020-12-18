using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using weather_app.Models;
using weather_app.Repositories.Interfaces;
using weather_app.Models.Dtos;
using Microsoft.AspNetCore.Http;
using weather_app.Filters;

namespace weather_app.Controllers
{
    public class AccountController : Controller
    {
        private readonly IUserRepository _repo;

        public AccountController(IUserRepository repo)
        {
            _repo = repo;
        }

#nullable enable
        public IActionResult Index(LoginDto? loginDto)
        {
            if (HttpContext.Session.GetInt32("id").HasValue)
            {
                return Redirect("/WeatherForecast/Index");
            }
            return View(loginDto);
        }

        [HttpPost]
        public async Task<IActionResult> Login(LoginDto loginDto)
        {
            var user = await _repo.LoginUserWithEmailAndPassword(loginDto.Email, loginDto.Password);
            if (user != null)
            {
                HttpContext.Session.SetInt32("id", user.Id);
                HttpContext.Session.SetString("name", user.FullName);
                HttpContext.Session.SetInt32("isAdmin", user.IsAdmin ? 1 : 0);
                return Redirect("/WeatherForecast/Index");
            }
            loginDto.ErrorMessage = "Wrong Email,Username or Password";
            loginDto.Password = "";
            return PartialView("Index", loginDto);

        }

        [HttpPost]
        public async Task<IActionResult> SignUp(RegisterDto RegisterDto)
        {
            if (!await _repo.CheckUserFromEmail(RegisterDto.Email))
            {
                RegisterDto.ErrorMessage = "This Email is already in use!";
                return PartialView("Register", RegisterDto);
            }
            else if (!await _repo.CheckUserFromUsername(RegisterDto.UserName))
            {
                RegisterDto.ErrorMessage = "This Username is already in use!";
                return PartialView("Register", RegisterDto);
            }
            await _repo.SaveUser(new User
            {
                FullName = RegisterDto.FullName,
                Email = RegisterDto.Email,
                UserName = RegisterDto.UserName,
                Password = RegisterDto.Password
            });
            return Redirect("Index");
        }

#nullable enable
        public IActionResult Register(RegisterDto? registerDto)
        {
            if (HttpContext.Session.GetInt32("id").HasValue)
            {
                return Redirect("/WeatherForecast/Index");
            }
            return View(registerDto);
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }


        public IActionResult LogOut()
        {
            HttpContext.Session.Clear();
            return Redirect("/Account/Index");
        }
    }
}
