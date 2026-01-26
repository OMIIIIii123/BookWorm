import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ThemeProvider } from "./Components/ThemeContext";
import Register from "./Components/Register";
import Cart from "./Components/Cart";
import Layout from "./Components/Layout";
import Home from "./Components/Home";
import Login from "./Components/Login";
import MyShelf from "./Components/MyShelf";
import MyLibrary from "./Components/MyLibrary";

function App() {
  return (
    <ThemeProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/" element={<Login />} />
          <Route path="/forgot-password" element={<div>Forgot Password Page</div>} />
          <Route path="/myshelf" element={<Layout><MyShelf /> </Layout>} />
          <Route path="/mylibrary" element={<Layout><MyLibrary /></Layout>} />                                        
          <Route path="/home" element={<Layout> <Home /> </Layout>} />
          <Route path="/cart" element={<Layout> <Cart /> </Layout>} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
