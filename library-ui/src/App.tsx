import { Routes, Route, Navigate } from "react-router-dom";
import BookListPage from "./pages/BookListPage";
import BookDetailPage from "./pages/BookDetailPage";
import BookNewPage from "./pages/BookNewPage";
import BookEditPage from "./pages/BookEditPage";

export default function App() {
    return (
        <div className="mx-auto max-w-3xl">
            <Routes>
                <Route path="/" element={<BookListPage />} />
                <Route path="/books/new" element={<BookNewPage />} />
                <Route path="/books/:id" element={<BookDetailPage />} />
                <Route path="/books/:id/edit" element={<BookEditPage />} />
                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </div>
    );
}