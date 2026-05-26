import { createReducer, on } from '@ngrx/store';
import { AuthCollaborator } from '../models/auth.model';
import { ResponseApi } from '../../../utils/types/apiResponse';
import { loadTodos, loadTodosError, loadTodosSuccess, toggleTodoComplete } from './auth.actions';

const initialState: ResponseApi<AuthCollaborator> = {
  data: { token: '' },
  statusCode: 0,
  message: '',
  loading: false,
  error: null,
  count: 0,
};

export const reducer = createReducer(
  initialState,

  on(loadTodos, (state) => ({
    ...state,
    loading: true,
    error: null,
  })),

  on(loadTodosSuccess, (state, { todos }) => ({
    ...state,
    ...todos,
    loading: false,
  })),

  on(loadTodosError, (state, { error }) => ({
    ...state,
    error,
    loading: false,
  })),

  on(toggleTodoComplete, (state, { id }) => ({
    ...state,
  })),
);
