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

namespace weather_app.Controllers
{
    public class AccountController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly IUserRepository _repo;

        public AccountController(ILogger<HomeController> logger, IUserRepository repo)
        {
            _logger = logger;
            _repo = repo;
        }

#nullable enable
        public IActionResult Index(LoginDto? loginDto)
        {
            return View(loginDto);
        }

        [HttpPost]
        public async Task<IActionResult> Login(LoginDto loginDto)
        {
            var user = await _repo.LoginUserWithEmailAndPassword(loginDto.Email, loginDto.Password);
            if (user != null)
            {
                HttpContext.Session.SetInt32("id",user.Id);
                HttpContext.Session.SetString("name",user.FullName);
                return Redirect("/Home/Index");
            }
            loginDto.ErrorMessage = "Wrong Email,Username or Password";
            loginDto.Password = "";
            return PartialView("Index", loginDto);

        }

        [HttpPost]
        public async Task<IActionResult> SignUp(RegisterDto RegisterDto)
        {
            if (!await _repo.CheckUserFromEmail(RegisterDto.User.Email))
            {
                return PartialView("Register", new RegisterDto
                {
                    User = RegisterDto.User,
                    ErrorMessage = "This Email is already in use!"
                });
            }
            else if (!await _repo.CheckUserFromUsername(RegisterDto.User.UserName))
            {
                return PartialView("Register", new RegisterDto
                {
                    User = RegisterDto.User,
                    ErrorMessage = "This Username is already in use!"
                });
            }
            await _repo.SaveUser(RegisterDto.User);
            return Redirect("Index");
        }

#nullable enable
        public IActionResult Register(RegisterDto? registerDto)
        {
            return View(registerDto);
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }


        public IActionResult LogOut(){
            HttpContext.Session.Clear();
            return Redirect("Index");
        }
    }
}
