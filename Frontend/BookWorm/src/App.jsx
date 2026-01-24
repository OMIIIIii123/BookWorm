import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login";
import MyShelf from "./Components/MyShelf";
import MyLibrary from "./Components/MyLibrary";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />

        <Route path="/register" element={<div>Register Page</div>} />
        <Route path="/forgot-password" element={<div>Forgot Password Page</div>} />

        <Route path="/home" element={<div>Home Page</div>} />

        <Route path="/myshelf" element={<MyShelf />} />
        <Route path="/mylibrary" element={<MyLibrary />} />
      </Routes>
    </BrowserRouter>
  );
}
