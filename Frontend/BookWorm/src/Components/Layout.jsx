import Header from "./Header";
import Footer from "./Footer";

export default function Layout({ children }) {
  return (
    <div style={{ minHeight: '100vh', background: 'var(--bg-light)' }}>
      <Header />
      {children}
      <Footer />
    </div>
  );
}
