import React, { useMemo, useState } from "react";
import { Link } from "react-router-dom";
import "./MyLibrary.css";

export default function MyLibrary() {
    const [activeTab, setActiveTab] = useState("all");

    // sample data (baad me API se aayega)
    const books = [
        {
            id: 1,
            title: "1984",
            author: "George Orwell",
            type: "ebook", // ebook | audio
            status: "owned", // owned | rented | lended
            badge: "Permanent",
            progress: 100,
            cover:
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?q=80&w=400&auto=format&fit=crop",
            btn1: "Read Again",
            btn2: "Lend",
            disabled2: false,
        },
        {
            id: 2,
            title: "Dune",
            author: "Frank Herbert",
            type: "audio",
            status: "owned",
            badge: "Audiobook",
            progress: 0,
            cover:
                "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?q=80&w=400&auto=format&fit=crop",
            btn1: "Listen",
        },
        {
            id: 3,
            title: "The Alchemist",
            author: "Paulo Coelho",
            type: "ebook",
            status: "rented",
            badge: "Rented • Expires in 2h",
            progress: 89,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Finish",
            btn2: "Extend",
            disabled2: false,
        },
        {
            id: 4,
            title: "Sapiens: A Brief History",
            author: "Yuval Noah Harari",
            type: "ebook",
            status: "lended",
            badge: "Lended",
            progress: 0,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Unavailable",
            disabled1: true,
        },
        {
            id: 5,
            title: "Thinking, Fast and Slow",
            author: "Daniel Kahneman",
            type: "audio",
            status: "rented",
            badge: "Audiobook • Rented",
            progress: 12,
            cover:
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?q=80&w=400&auto=format&fit=crop",
            btn1: "Listen",
        },
        {
            id: 6,
            title: "The Great Gatsby",
            author: "F. Scott Fitzgerald",
            type: "ebook",
            status: "owned",
            badge: "Permanent",
            progress: 45,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Read Now",
        },
    ];

    const counts = useMemo(() => {
        return {
            all: books.length,
            ebooks: books.filter((b) => b.type === "ebook").length,
            audio: books.filter((b) => b.type === "audio").length,
            rented: books.filter((b) => b.status === "rented").length,
            lended: books.filter((b) => b.status === "lended").length,
        };
    }, [books]);

    const filteredBooks = useMemo(() => {
        if (activeTab === "all") return books;
        if (activeTab === "ebooks") return books.filter((b) => b.type === "ebook");
        if (activeTab === "audio") return books.filter((b) => b.type === "audio");
        if (activeTab === "rented") return books.filter((b) => b.status === "rented");
        if (activeTab === "lended") return books.filter((b) => b.status === "lended");
        return books;
    }, [activeTab, books]);

    return (
        <div className="lib-page">
            {/* NAVBAR */}
            <div className="lib-navbar">
                <div className="lib-navbar-inner">
                    <div className="brand">
                        <div className="logo">📚</div>
                        Bookworm
                    </div>

                    <div className="nav-links">
                        <Link to="/home">Discover</Link>
                        <Link to="/mylibrary" className="active">
                            My Library
                        </Link>
                        <Link to="/myshelf">My Shelf</Link>
                    </div>

                    <div className="nav-actions">
                        <button className="icon-btn" title="Search">🔍</button>
                        <button className="icon-btn" title="Notifications">🔔</button>
                        <button className="icon-btn" title="Profile">👤</button>
                    </div>
                </div>
            </div>

            {/* MAIN */}
            <div className="lib-container">
                <h1 className="lib-title">My Library</h1>
                <p className="lib-subtitle">
                    Manage your books, rentals and lended items.
                </p>

                {/* TABS */}
                <div className="tabs-row">
                    <button
                        className={`tab-btn ${activeTab === "all" ? "active" : ""}`}
                        onClick={() => setActiveTab("all")}
                    >
                        All Items <span className="tab-count">{counts.all}</span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "ebooks" ? "active" : ""}`}
                        onClick={() => setActiveTab("ebooks")}
                    >
                        eBooks <span className="tab-count">{counts.ebooks}</span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "audio" ? "active" : ""}`}
                        onClick={() => setActiveTab("audio")}
                    >
                        Audiobooks <span className="tab-count">{counts.audio}</span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "rented" ? "active" : ""}`}
                        onClick={() => setActiveTab("rented")}
                    >
                        Rented <span className="tab-count">{counts.rented}</span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "lended" ? "active" : ""}`}
                        onClick={() => setActiveTab("lended")}
                    >
                        Lended <span className="tab-count">{counts.lended}</span>
                    </button>
                </div>

                {/* GRID */}
                <div className="books-grid">
                    {filteredBooks.map((b) => (
                        <div className="book-card" key={b.id}>
                            <div className="book-top">
                                <div className="book-cover">
                                    <img src={b.cover} alt={b.title} />
                                </div>

                                <div className="book-info">
                                    <div className="book-title">{b.title}</div>
                                    <div className="book-author">{b.author}</div>
                                    <div className="badge">{b.badge}</div>
                                </div>
                            </div>

                            <div className="progress-wrap">
                                <div className="progress-row">
                                    <span>Progress</span>
                                    <span>{b.progress === 100 ? "Completed" : `${b.progress}%`}</span>
                                </div>

                                <div className="progress-bar">
                                    <div
                                        className="progress-fill"
                                        style={{ width: `${b.progress}%` }}
                                    />
                                </div>
                            </div>

                            <div className="card-actions">
                                <button
                                    className={`btn-primary ${b.disabled1 ? "btn-disabled" : ""}`}
                                    disabled={b.disabled1}
                                >
                                    {b.btn1}
                                </button>

                                {b.btn2 ? (
                                    <button
                                        className={`btn-outline ${b.disabled2 ? "btn-disabled" : ""}`}
                                        disabled={b.disabled2}
                                    >
                                        {b.btn2}
                                    </button>
                                ) : (
                                    <div />
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* FOOTER */}
            <div className="lib-footer">
                <p>© {new Date().getFullYear()} Bookworm.com. All rights reserved.</p>
            </div>
        </div>
    );
}
