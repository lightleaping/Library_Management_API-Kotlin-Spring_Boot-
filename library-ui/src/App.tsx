import { Routes, Route, Navigate } from "react-router-dom";
import BookListPage from "./pages/BookListPage";
import BookDetailPage from "./pages/BookDetailPage";

export default function App() {
    return (
        <Routes>
            <Route path="/" element={<BookListPage />} />
            <Route path="/books/:id" element={<BookDetailPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    );
}